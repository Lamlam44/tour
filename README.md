Nhớ dùng mapper để mapping từ entity sang dto
Thao tác kiểm tra thông tin (logic nghiệp vụ) là thực hiện với dto, còn thao tác update csdl là dùng đối tượng mapper (ví dụ "Account accountEntity = accountMapper.toEntity(dto);" dùng accountEntity để gọi repository để update csdl)
Lớp controller cugz v
