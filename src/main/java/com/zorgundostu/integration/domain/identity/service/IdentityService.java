package com.zorgundostu.integration.domain.identity.service;

import com.zorgundostu.integration.domain.identity.client.IdentityClient;
import com.zorgundostu.integration.domain.identity.model.identity.IdentityDto;
import com.zorgundostu.integration.domain.identity.repository.IdentityRepository;
import com.zorgundostu.integration.domain.identity.util.mapper.IdentityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IdentityService {

    private final IdentityMapper identityMapper;
    private final IdentityRepository identityRepository;
    private final IdentityClient identityClient;

    public Page<IdentityDto> getAllIdentities(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");
        return identityRepository.findAll(pageable)
                .map(identityMapper::toDto);
    }

    public boolean verifyIdentityNumber(IdentityDto dto) {
        boolean result = identityClient.verifyIdentityNumber(dto);
        var identity = identityMapper.toDao(dto);
        identity.setResult(result);
        var savedIdentity = identityRepository.save(identity);
        return result;
    }
}
