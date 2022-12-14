
package com.travellyprueba.travellyprueba.Controller;

import com.travellyprueba.travellyprueba.Entity.Pais;
import com.travellyprueba.travellyprueba.Repository.PaisRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/api/pais")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaisController {
    
    @Autowired
    private PaisRepository paisRepository;
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Pais> obtenerPaisPorId(@PathVariable Integer id){
        Optional<Pais> paisOptional = paisRepository.findById(id);
        
        if(!paisOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        
        return ResponseEntity.ok(paisOptional.get());
        
    }
    
    @GetMapping("/listar")
    public ResponseEntity <List<Pais>> listarPaises(){
        List<Pais> list = paisRepository.findAll();
        return new ResponseEntity(list,HttpStatus.OK);
    }
    
    
    @PostMapping("/crear")
    public ResponseEntity<Pais> guardarPais(@Valid @RequestBody Pais pais){
        Pais paisGuardado = paisRepository.save(pais);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(paisGuardado.getId()).toUri();
        
        return ResponseEntity.created(ubicacion).body(paisGuardado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Pais> editarPais(@PathVariable Integer id, @Valid @RequestBody Pais pais){
        Optional<Pais> paisOptional = paisRepository.findById(id);
        
        if(!paisOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        
        pais.setId(paisOptional.get().getId());
        paisRepository.save(pais);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Pais> eliminarPais(@PathVariable Integer id){
        Optional<Pais> paisOptional = paisRepository.findById(id);
        
        if(!paisOptional.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }
        
        paisRepository.delete(paisOptional.get());
        return ResponseEntity.noContent().build();
    }
}
