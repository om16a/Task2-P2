package org.example.jwt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDTO {
    private Long submissionId;
    private Long userId;
    private String language;
    private String code;
    private Long problemId;
    public Boolean checkNull()
    {
        return submissionId == null || userId == null || languageId == null || code == null;
    }

}