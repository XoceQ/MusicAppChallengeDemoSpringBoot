package com.example.musicapp.util;

import com.example.musicapp.dto.SingerDTO;
import com.example.musicapp.dto.SongDTO;
import com.example.musicapp.service.MusicAppService;

import java.util.Scanner;

public class ConsoleMenu {
    private final MusicAppService musicAppService;
    private final Scanner scanner;

    public ConsoleMenu(MusicAppService musicAppService) {
        this.musicAppService = musicAppService;
        this.scanner = new Scanner(System.in);
    }


    public void showMenu() {
        int option = -1;
        while (option != 0) {
            // Mostrar el menú cada vez que se complete una acción
            System.out.println("\n=== Menú de la Aplicación de Música ===");
            String menu = """
                    1 - Registrar un cantante
                    2 - Registrar una canción
                    3 - Buscar canciones por cantante
                    4 - Mostrar todos los cantantes
                    5 - Mostrar todas las canciones
                    0 - Salir
                    """;
            System.out.println(menu);
            System.out.print("Selecciona una opción: ");

            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer de entrada

                switch (option) {
                    case 1:
                        registerSinger();
                        break;
                    case 2:
                        registerSong();
                        break;
                    case 3:
                        searchSongsBySinger();
                        break;
                    case 4:
                        displayAllSingers();
                        break;
                    case 5:
                        displayAllSongs();
                        break;
                    case 0:
                        System.out.println("Saliendo de la aplicación...");
                        System.exit(0); // Termina la aplicación
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }

            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingresa un número.");
                scanner.nextLine(); // Limpiar el buffer en caso de error
            }
        }
    }

    private void registerSinger() {
        System.out.println("\n=== Registrar un Cantante ===");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Género: ");
        String genre = scanner.nextLine();
        System.out.print("Nacionalidad: ");
        String nationality = scanner.nextLine();

        SingerDTO singerDTO = new SingerDTO();
        singerDTO.setName(name);
        singerDTO.setGenre(genre);
        singerDTO.setNationality(nationality);

        try {
            musicAppService.addSinger(singerDTO);
            System.out.println("Cantante registrado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al registrar el cantante: " + e.getMessage());
        }
    }

    private void registerSong() {
        System.out.println("\n=== Registrar una Canción ===");
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Álbum: ");
        String album = scanner.nextLine();
        System.out.print("ID del cantante: ");
        Long singerId = scanner.nextLong();
        scanner.nextLine(); // Limpiar el buffer

        SongDTO songDTO = new SongDTO();
        songDTO.setTitle(title);
        songDTO.setAlbum(album);
        songDTO.setSingerId(singerId);

        try {
            musicAppService.addSong(songDTO);
            System.out.println("Canción registrada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al registrar la canción: " + e.getMessage());
        }
    }

    private void searchSongsBySinger() {
        System.out.println("\n=== Buscar Canciones por Cantante ===");
        System.out.print("Nombre del cantante: ");
        String singerName = scanner.nextLine();

        try {
            var songs = musicAppService.findSongsBySinger(singerName);
            if (songs.isEmpty()) {
                System.out.println("No se encontraron canciones para el cantante: " + singerName);
            } else {
                System.out.println("Canciones encontradas:");
                songs.forEach(song -> System.out.println("- " + song.getTitle() + " (Álbum: " + song.getAlbum() + ")"));
            }
        } catch (Exception e) {
            System.err.println("Error al buscar canciones: " + e.getMessage());
        }
    }

    private void displayAllSingers() {
        System.out.println("\n=== Lista de Todos los Cantantes ===");
        var singers = musicAppService.getAllSingers();
        if (singers.isEmpty()) {
            System.out.println("No hay cantantes registrados.");
        } else {
            singers.forEach(singer -> System.out.println("- ID: " + singer.getId() + ", Nombre: " + singer.getName()));
        }
    }

    private void displayAllSongs() {
        System.out.println("\n=== Lista de Todas las Canciones ===");
        var songs = musicAppService.getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("No hay canciones registradas.");
        } else {
            songs.forEach(song -> System.out.println("- Título: " + song.getTitle() + ", Álbum: " + song.getAlbum()));
        }
    }
}
