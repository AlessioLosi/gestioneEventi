package com.gestioneEventi.Services;

import com.gestioneEventi.Entities.User;
import com.gestioneEventi.Exceptions.BadRequestException;
import com.gestioneEventi.Exceptions.NotFoundException;
import com.gestioneEventi.Payloads.NewUserDTO;
import com.gestioneEventi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UserRepository uR;

    public User save(NewUserDTO body) {
        this.uR.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        User newUser = new User(body.nome(), body.cognome(), body.email(), body.password(),
                body.ruolo());

        return this.uR.save(newUser);
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.uR.findAll(pageable);
    }

    public User findById(UUID userId) {
        return this.uR.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, NewUserDTO body) {
        User found = this.findById(userId);

        if (!found.getEmail().equals(body.email())) {
            this.uR.findByEmail(body.email()).ifPresent(
                    user -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }

        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setPassword(body.password());


        return this.uR.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.uR.delete(found);
    }

    public User findByEmail(String email) {
        return this.uR.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }


}
