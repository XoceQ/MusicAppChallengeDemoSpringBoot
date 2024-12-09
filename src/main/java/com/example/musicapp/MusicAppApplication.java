package com.example.musicapp;

import com.example.musicapp.service.MusicAppService;
import com.example.musicapp.util.ConsoleMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MusicAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MusicAppApplication.class, args);
		MusicAppService musicAppService = context.getBean(MusicAppService.class);

		ConsoleMenu consoleMenu = new ConsoleMenu(musicAppService);
		consoleMenu.showMenu();
	}

}
