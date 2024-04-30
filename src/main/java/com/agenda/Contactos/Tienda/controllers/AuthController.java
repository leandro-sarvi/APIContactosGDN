package com.agenda.Contactos.Tienda.controllers;
import com.agenda.Contactos.Tienda.dto.UserDto;
import com.agenda.Contactos.Tienda.services.IAuthService;
import com.agenda.Contactos.Tienda.dto.LoginDto;
import com.agenda.Contactos.Tienda.dto.ResponseDto;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;
    public AuthController(IAuthService authService){
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDto loginDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        HashMap<String,String> login = authService.login(loginDto);
        if(login.containsKey("jwt")){
            return ResponseEntity.status(HttpStatus.OK).body(login);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(login);
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserDto userDto) throws Exception {
        ResponseDto responseDto = this.authService.register(userDto);
        if(responseDto.getError()>0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping("/confirm-mail/{token}")
    @ResponseBody
    public HashMap<String, String> confirmarCorreo(@PathVariable("token") String token) {
        // Aquí puedes implementar la lógica para validar el token
        // y confirmar la dirección de correo electrónico del usuario
        HashMap<String,String> response = this.authService.confirmEmail(token);
        return response;
    }
    @PostMapping("/userdelete/{id}")
    public ResponseEntity<ResponseDto> deleteUserById(@PathVariable Long id,@RequestBody String password){
        ResponseDto response = this.authService.deleteUser(id, password);
        if(response.getError()!=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/admin-access")
    public String adminAccess(){
        return "Acceso concedido";
    }

    @GetMapping("/sessionverifier")
    public Authentication sessionVerifier(){
        Authentication session = this.authService.sessionVerifier();
        return session;
    }
}
