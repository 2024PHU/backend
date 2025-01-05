package com.phu.backend.repository.summarization;

import com.phu.backend.domain.summarization.Summarization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummarizationRepository extends JpaRepository<Summarization, Long> {
}
