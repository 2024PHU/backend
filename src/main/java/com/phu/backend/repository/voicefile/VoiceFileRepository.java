package com.phu.backend.repository.voicefile;

import com.phu.backend.domain.voicefile.VoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceFileRepository extends JpaRepository<VoiceFile, Long> {
}
