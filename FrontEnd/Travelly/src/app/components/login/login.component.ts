import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { LoginUsuario } from 'src/app/model/login-usuario';
import { AuthService } from 'src/app/service/auth.service';
import { TokenService } from 'src/app/service/token.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLogged = false;
  isLogginFail = false;
  loginUsuario!: LoginUsuario;
  nombreUsuario!: string;
  password!: string;
  roles: string[] = [];
  errMsj!: string;

  constructor(private tokenService: TokenService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    if(this.tokenService.getToken()){
      this.isLogged = true;
      this.isLogginFail = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  onLogin(): void {
    this.loginUsuario = new LoginUsuario(this.nombreUsuario, this.password);
    this.authService.login(this.loginUsuario).subscribe(data =>{
      this.isLogged = true;
      this.isLogginFail = false;
      this.tokenService.setToken(data.token);
      this.tokenService.setUserName(data.nombreUsuario);
      this.tokenService.setAuthorities(data.authorities);
      this.roles = data.authorities;
      Swal.fire({
        icon: 'success',
        title: 'Se ha iniciado sesion correctamente!',
        showConfirmButton: false,
        timer: 1500
      })
      //console.log(JSON.stringify(data.authorities[0]));
      if(JSON.stringify(data.authorities[0]) == '{"authority":"ROLE_ADMIN"}'){
        this.router.navigate(['admin'])
      } else if(JSON.stringify(data.authorities [1])== '{"authority":"ROLE_ADMIN"}'){
        this.router.navigate(['admin'])
      }else{
        this.router.navigate([''])
      }
    }, err =>{
      this.isLogged = false;
      this.isLogginFail = true;
      this.errMsj = err.error.mensaje;
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Algo salio mal! revise los campos nuevamente...',
      })
    })

  }

  mensaje(): void {
    Swal.fire('Componente no disponible momentaneamente')
  }

}
