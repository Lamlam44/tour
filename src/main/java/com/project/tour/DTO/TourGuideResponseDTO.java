package com.project.tour.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourGuideResponseDTO {

    private String tourGuideId;
    private String tourGuideName;
    private String tourGuideEmail;
    private String tourGuidePhone;
    private int tourGuideExperienceYears;

    // Lưu ý: Chúng ta không trả về danh sách các Tour ở đây để
    // tránh dữ liệu lớn và lỗi vòng lặp. Nếu cần danh sách
    // các tour của một hướng dẫn viên, hãy tạo một API riêng.
    // Ví dụ: GET /api/tour-guides/{guideId}/tours
}