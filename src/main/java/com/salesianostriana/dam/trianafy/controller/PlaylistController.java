package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.*;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongService songService;


    @Operation(summary = "Obtiene todas las PlayList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se han encontrado todas las PlayList",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Playlist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "PlayList no encontradas",
                    content = @Content) })
    @GetMapping("/list")
    public ResponseEntity<List<AllPlaylistsResponseDTO>> obtenerTodas(){
        List<Playlist> result = playlistService.findAll();
        if (result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result.stream().map(AllPlaylistsResponseDTO::of).collect(Collectors.toList()));
        }
    }


    @Operation(summary = "Obtiene una PlayList a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha encontrado la PlayList",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Playlist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "PlayList no encontrada",
                    content = @Content) })
    @GetMapping("/list/{id}")
    public ResponseEntity<OnePlaylistResponseDTO> obtenerUna (@PathVariable Long id){
        Playlist result = playlistService.findById(id).orElse(null);
        if (result == null){
            return ResponseEntity.notFound().build();
        }else
            return ResponseEntity.of(playlistService.findById(id).map(OnePlaylistResponseDTO::of));
    }


    @Operation(summary = "Crea una nueva PlayList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se ha creado una nueva PlayList",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Playlist.class))
                    ) }),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos son errçoneos",
                    content = @Content) })
    @PostMapping("/list")
    public ResponseEntity<BasicPlaylistResponseDTO> nuevaPlaylist (@RequestBody OnePlaylistRequestDTO nuevo){
        if(nuevo.getName() == ""){
            return ResponseEntity.badRequest().build();
        }
        Playlist saved = playlistService.add(nuevo.toPlaylist());

        return ResponseEntity.status(HttpStatus.CREATED).body(BasicPlaylistResponseDTO.of(saved));
    }
 //una cosa es lo que envio (oneplaylist) y otra cosa es lo que tiene que devolverme (basicplaylist que contiene el id)
  //  además PlayList es distinta a estas por lo tanto tengo que transformarlas


    @Operation(summary = "Edita una PlayList a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha modificado la PlayList",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Playlist.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "PlayList no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos son erróneos",
                    content = @Content)})
    @PutMapping("/list/{id}")
    public ResponseEntity<OnePlaylistResponseDTO> editarPlaylist (@RequestBody OnePlaylistResponseDTO editar, @PathVariable Long id){
        if(editar.getName()== ""){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.of(playlistService.findById(id).map(p -> {
                p.setName((editar.getName()));
                p.setDescription((editar.getDescription()));
                return OnePlaylistResponseDTO.of(playlistService.edit(p));
            }));
        }
    }

    @Operation(summary = "Elimina a una PlayList a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "PlayList eliminada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Playlist.class))
                    ) }), })
    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> borrarArtista(@PathVariable Long id){
        if (playlistService.findById(id).isPresent()){
            playlistService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene las canciones de una PlayList a partir de un id dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OnePlaylistResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "PlayList no encontrada",
                    content = @Content) })
    @GetMapping("/list/{id}/song")
    public ModelAndView verCancionesPlaylist(@PathVariable Long id){
        return new ModelAndView("redirect:/list/" + id);
    }
    //también vale copiando y pegando el mismo método



    @GetMapping("/list/{id}/song/{idSong}")
    public ResponseEntity<OneSongResponseDTO> obtenerUnaCancion (@PathVariable Long id, @PathVariable Long idSong){
       return  ResponseEntity.of(songService.findById(idSong).map(OneSongResponseDTO::of));
    }
    // return new ModelAndView("redirect:/song/" + idSong)

    @Operation(summary = "Añade una canción a una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha añadido una canción a la PlayList",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = OnePlaylistResponseDTO.class))
                    ) }),
            @ApiResponse(responseCode = "404", description = "No se encuentra la playlist o la canción",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Los datos introducidos son erróneos",
                    content = @Content) })
    @PostMapping("/list/{id}/song/{idSong}")
    public ResponseEntity<OnePlaylistResponseDTO> sumarCancion(@PathVariable Long id, @PathVariable Long idSong){
        if(songService.findById(idSong).isPresent()){
            return ResponseEntity.of(
                    playlistService.findById(id).map(playlist -> {
                        playlist.addSong(songService.findById(idSong).get());
                        return OnePlaylistResponseDTO.of(playlistService.edit(playlist)); //se guardan los cambios realizados(la cancion añadida)
                    })
            );
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Elimina una canción de una PlayList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Canción eliminada",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No se encuentra la playlist o la canción",
                    content = @Content) })
    @DeleteMapping("/list/{id}/song/{idSong}")
    public ResponseEntity<?> borrarDePlaylist(@PathVariable Long id, @PathVariable Long idSong){
        return playlistService.findById(id).map(playlist -> {
            if (songService.findById(idSong).isPresent()){
                Song borrar = songService.findById(idSong).get();   //el get te saca del optional la info
                if(playlist.getSongs().contains(borrar)){
                    playlist.deleteSong(borrar);
                    playlistService.edit(playlist); //se guardan los cambios realizados (el borrado)
                }
            }
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }




}
