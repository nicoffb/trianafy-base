package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @Operation(summary = "Obtiene todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se han encontrado todos los artistas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Artist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "Artistas no encontrados",
                    content = @Content) })
   @GetMapping("/artist")
   public ResponseEntity<?> obtenerTodos(){
       List<Artist> result = artistService.findAll();
       if (result.isEmpty()){
           return ResponseEntity.notFound().build();
       }else{
           return ResponseEntity.ok(result);
       }
   }

   @GetMapping("/artist/{id}")
    public ResponseEntity<?> obtenerUno (@PathVariable Long id){
       Artist result = artistService.findById(id).orElse(null);
       if (result == null){
           return ResponseEntity.notFound().build();
       }else
           return ResponseEntity.ok(result);
   }

   @PostMapping("/artist")
    public ResponseEntity<Artist> nuevoArtista (@RequestBody Artist nuevo){
       if(nuevo.getName() == ""){
           return ResponseEntity.badRequest().build();
       }
       Artist saved = artistService.add(nuevo);
       return ResponseEntity.status(HttpStatus.CREATED).body(saved);
   }

   @PutMapping("/artist/{id}")
    public ResponseEntity<?> editarArtista (@RequestBody Artist editar, @PathVariable Long id){
        return artistService.findById(id).map(a -> {
            a.setName((editar.getName()));
            return ResponseEntity.ok(artistService.add(a));
        }).orElseGet(() -> {
            return ResponseEntity.notFound().build();
        });
   }

   @DeleteMapping("/artist/{id}")
    public ResponseEntity<?> borrarArtista(@PathVariable Long id){

       artistService.deleteById(id);
       return ResponseEntity.noContent().build();
   }

}
