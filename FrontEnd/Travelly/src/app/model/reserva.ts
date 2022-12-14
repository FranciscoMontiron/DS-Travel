import { LoginUsuario } from "./login-usuario";
import { Usuario } from "./usuario";
import { Vuelo } from "./vuelo";

export class Reserva {
    
    id?: number;
    estado: string;
    fechaYHora: Date;
    usuario: Usuario;
    vuelo: Vuelo;

    constructor(estado: string, fechaYHora: Date, usuario: Usuario, vuelo: Vuelo){
        this.estado = estado;
        this.fechaYHora = fechaYHora;
        this.usuario = usuario;
        this.vuelo = vuelo;
    }
    
}
