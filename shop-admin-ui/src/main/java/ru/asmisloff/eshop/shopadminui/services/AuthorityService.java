package ru.asmisloff.eshop.shopadminui.services;

import org.springframework.stereotype.Service;
import ru.asmisloff.eshop.shopdatabase.entities.Authority;
import ru.asmisloff.eshop.shopdatabase.repositories.AuthorityRepository;

import java.util.List;

@Service
public class AuthorityService {

    AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public void save(Authority authority) {
        if (findByName(authority.getName()) != null) {
            return;
        }
        authorityRepository.save(authority);
    }

    public void saveAll(List<Authority> authorities) {
        authorityRepository.saveAll(authorities);
    }

    public void deleteById(Long id) {
        authorityRepository.deleteById(id);
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public Authority findByName(String authorityName) {
        return authorityRepository.findByName(authorityName);
    }

    public Authority findById(Long id) {
        return authorityRepository.findById(id).orElse(null);
    }
}
