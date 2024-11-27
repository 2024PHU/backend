package com.phu.backend.repository.voicefile;

import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.voicefile.VoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoiceFileRepository extends JpaRepository<VoiceFile, Long> {
    List<VoiceFile> findAllByTrainerAndMemberId(Member member, Long memberId);
}
