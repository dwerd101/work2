package com.example.dao;

import java.time.LocalDate;

public interface ProfileResultService {
     void saveProfileResultDomain(String domain, Long id);
     void saveProfileResult(Long fieldId, LocalDate dateField, String domain, String text);
}
