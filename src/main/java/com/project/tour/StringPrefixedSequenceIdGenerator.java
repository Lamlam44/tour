// FileName: StringPrefixedSequenceIdGenerator.java
// Package: com.project.tour.generator (Hãy chắc chắn package này đúng)

package com.project.tour;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

// Dùng "TABLE" (bảng) thay vì "SEQUENCE"
public class StringPrefixedSequenceIdGenerator implements IdentifierGenerator {

    public static final String SEQUENCE_PARAM = "sequence_name";
    public static final String PREFIX_PARAM = "prefix_value";
    public static final String NUMBER_FORMAT_PARAM = "number_format";

    private String sequenceName;
    private String prefixValue;
    private String numberFormat;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        sequenceName = params.getProperty(SEQUENCE_PARAM);
        if (sequenceName == null || sequenceName.isEmpty()) {
            throw new MappingException("Tham số 'sequence_name' không được để trống.");
        }
        
        prefixValue = params.getProperty(PREFIX_PARAM, "");
        numberFormat = params.getProperty(NUMBER_FORMAT_PARAM, "%d");
    }

    /**
     * Hàm generate() ĐÃ SỬA LẠI HOÀN TOÀN
     * Dành riêng cho MySQL (đọc/ghi vào bảng giả lập sequence)
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        
        Connection connection = null;
        try {
            // Tên của bảng giả lập sequence (ví dụ: "tour_seq")
            String sequenceTableName = sequenceName;

            // 1. Lấy kết nối JDBC
            connection = session.getJdbcConnectionAccess().obtainConnection();

            // 2. Lấy giá trị hiện tại và KHÓA HÀNG ĐÓ LẠI (SELECT ... FOR UPDATE)
            //    Đây là bước tối quan trọng để chống "race condition"
            long nextValue;
            try (Statement selectStatement = connection.createStatement()) {
                String selectSql = "SELECT next_val FROM " + sequenceTableName + " FOR UPDATE";
                try (ResultSet rs = selectStatement.executeQuery(selectSql)) {
                    if (!rs.next()) {
                        throw new HibernateException("Bảng sequence '" + sequenceTableName + "' chưa được khởi tạo (chưa có 'insert into ... values (1)')");
                    }
                    nextValue = rs.getLong(1);
                }
            }

            // 3. Cập nhật giá trị mới (cộng 1)
            try (Statement updateStatement = connection.createStatement()) {
                String updateSql = "UPDATE " + sequenceTableName + " SET next_val = " + (nextValue + 1);
                updateStatement.executeUpdate(updateSql);
            }

            // 4. Trả về giá trị đã lấy (trước khi cộng) để ghép chuỗi
            String formattedNumber = String.format(numberFormat, nextValue);
            return prefixValue + formattedNumber;

        } catch (SQLException e) {
            throw new HibernateException("Lỗi SQL khi tạo ID: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new HibernateException("Lỗi khi tạo ID: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    // Thả kết nối
                    session.getJdbcConnectionAccess().releaseConnection(connection);
                } catch (SQLException e) {
                    // (Bỏ qua lỗi khi release)
                }
            }
        }
    }
}