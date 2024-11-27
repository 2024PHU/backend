package com.phu.backend.repository.clovavoicetext;

import com.phu.backend.domain.clovavoicetext.ClovaVoiceText;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClovaVoiceTextRepository extends CrudRepository<ClovaVoiceText, UUID> {

}
