package com.gestioneEventi.Services;

import com.gestioneEventi.Entities.User;
import com.gestioneEventi.Exceptions.UnauthorizedException;
import com.gestioneEventi.Payloads.NewUserLoginDTO;
import com.gestioneEventi.Tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWT jwt;

    public String checkCredentialsAndGenerateToken(NewUserLoginDTO body) {
        User found = this.usersService.findByEmail(body.email());
        if (found.getPassword().equals(body.password())) {
            String accessToken = jwt.createToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
