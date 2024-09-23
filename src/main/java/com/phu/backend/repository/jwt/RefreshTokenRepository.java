package com.phu.backend.repository.jwt;

import com.phu.backend.domain.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
