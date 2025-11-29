package com.project.tour.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Upload image to Cloudinary
     * 
     * @param file   MultipartFile from form
     * @param folder Folder name in Cloudinary (e.g., "tours", "accommodations")
     * @return Cloudinary URL of uploaded image
     */
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Generate unique filename
        String publicId = folder + "/" + UUID.randomUUID().toString();

        // Upload to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "public_id", publicId,
                        "folder", folder,
                        "resource_type", "auto"));

        // Return secure URL
        return (String) uploadResult.get("secure_url");
    }

    /**
     * Delete image from Cloudinary
     * 
     * @param imageUrl The full Cloudinary URL
     * @return true if deleted successfully
     */
    public boolean deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return false;
            }

            // Extract public_id from URL
            // Example URL:
            // https://res.cloudinary.com/demo/image/upload/v1234/tours/uuid.jpg
            String publicId = extractPublicIdFromUrl(imageUrl);

            if (publicId != null) {
                Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                return "ok".equals(result.get("result"));
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extract public_id from Cloudinary URL
     */
    private String extractPublicIdFromUrl(String imageUrl) {
        try {
            // Extract public_id from URL
            String[] parts = imageUrl.split("/upload/");
            if (parts.length > 1) {
                String pathAfterUpload = parts[1];
                // Remove version number (v1234567890/)
                String withoutVersion = pathAfterUpload.replaceFirst("v\\d+/", "");
                // Remove file extension
                int lastDotIndex = withoutVersion.lastIndexOf('.');
                if (lastDotIndex > 0) {
                    return withoutVersion.substring(0, lastDotIndex);
                }
                return withoutVersion;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Upload multiple images
     */
    public String[] uploadMultipleImages(MultipartFile[] files, String folder) throws IOException {
        String[] urls = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            urls[i] = uploadImage(files[i], folder);
        }
        return urls;
    }
}
