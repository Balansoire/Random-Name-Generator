package com.example.name_generator_api;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.name_generator_api.exception.BadArgsException;
import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.NotImplementedException;
import com.example.name_generator_api.factory.SectionalFactory;
import com.example.name_generator_api.model.NameList;
import com.example.name_generator_api.service.NameGeneratorService;
import com.example.name_generator_api.strategy.NaiveStrategy;
import com.example.name_generator_api.strategy.NameGenerationStrategy;
import com.example.name_generator_api.strategy.SectionalStrategy;

@SpringBootApplication
public class NameGeneratorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameGeneratorApiApplication.class, args);
		
		/**
		 NaiveStrategy naiveGenerator = new NaiveStrategy();
		SectionalStrategy sectionalGenerator = new SectionalStrategy(3);
		NameList list = new NameList();
		
		//Write name list here
		String string = "Jean Dupont, Marie Martin, Paul Bernard, Sophie Leroy, Lucas Moreau, Camille Petit, Thomas Durand, Laura Robert, Antoine Richard, Émilie Simon, Nicolas Laurent, Claire Michel, Julien Garcia, Pauline Lefèvre, Maxime Roux, Chloé Fournier, Alexandre Girard, Manon Andre, David Bonnet, Sarah Lambert, Hugo Perrot, Léa Gauthier, Mathis Chevalier, Inès Marchand, Romain Blanc, Anaïs Faure, Victor Lopez, Noémie Colin, Baptiste Henry, Clara Renaud, Kévin Masson, Julie Pichon, Adrien Carpentier, Morgane Lemoine, Florian Bouvier, Élise Guérin, Quentin Schmitt, Amandine Roche, Théo Muller, Vanessa Aubert, Loïc Sanchez, Justine Nguyen, Benjamin Torres, Ophélie Pelletier, Damien Leclerc, Mélanie Fontaine, Yannick Morel, Fanny Brun, Sébastien Paris, Lucie Vidal, Cédric Meunier, Marion Hoarau, Olivier Rolland, Sandrine Cousin, Pierre Maillard, Aurélia Chartier, Jérôme Arnaud, Pauline Texier, Frédéric Delorme, Hélène Bataille, Julien Poirier, Sonia Bret, Laurent Picard, Nadia Benali, Rémi Tanguy, Karine Savary, Stéphane Leduc, Valérie Jacquet, Anthony Noel, Isabelle Perrin, Mickaël Barbier, Élodie Pasquier, Samuel Rivière, Aurore Colin, Patrice Monnier, Céline Dumas, Armand Lefort, Laure Besson, Nicolas Chauvin, Sophie Delattre, Arthur Delmas, Lucile Vacher, Mathéo Coussy, Amélie Roussel, Kylian Huguet, Solène Barré, Nathan Coste, Maëlys Chardon, Valentin Pruvost, Éva Lhoste, Corentin Laborde, Romane Guilbaud, Dylan Franchini, Clémence Saunier, Axel Morin, Juliette Canet, Enzo Salomon, Margaux Aymard, Thibault Renier, Océane Poulain, Bastien Cormier, Anaëlle Briand, Loris Devaux, Capucine Allard, Robin Montoya, Perrine Lachaud, Tom Duflot, Alix Penin, Sacha Villiers, Coline Deschamps, Ilyes Hamdi, Maud Beaufils, Raphaël Cordier, Agathe Lorrain, Noah Chabert, Lison Fleury, Timothée Pons, Salomé Audin, Esteban Navarro, Maïa Legrand, Clément Vial, Iris Bonin, Yanis Azzouzi, Louise Giraudet, Paul-Henri Costes, Ninon Mallet, Mehdi Kaci, Albane Joubert, Gaëtan Marceau, Zoé Landry, Rayan Belkacem, Philippine Colette, Amaury Desprès, Elsa Vaillant, Sofiane Mokrani, Jeanne Boulay, Loïs Ferret, Apolline Grimaud, Karim Ouali, Faustine Noyer, Hugo Leprince, Thaïs Robert, Samir Haddad, Éléonore Picot, Alexis Briet, Manon Calvet, Farid Bensaïd, Clarisse Hémon, Maxence Jolivet, Yasmine Rahmani, Augustin Perrault, Bérénice Louvet, Bilal Aït Ali, Héloïse Marchal, Victorien Savin, Inaya Saidi, Benoît Chazal, Rosalie Hirtz, Anis Benyahia, Constance Rivet, Jérémy Bailleul, Émilienne Cros, Oussama El Idrissi, Sidonie Vernet, Florian Kuentz, Lila Montfort, Nabil Cherif, Aliénor Tissot, Matthieu Gandon, Dounia Khelifi, Séraphin Aubry, Suzanne Foucher, Mourad Lahlou, Bertille Guichard, Loïc Pradel, Yara Mansouri, Pierre-Antoine Rougier, Célestine Pavy, Adel Zouaoui, Ombeline Racine, Romain Thénot, Iman Ziani, Simon Kersalé, Clotilde Péron, Amine Boudjemaa, Joséphine Latour, Émeric Valois, Lina Bouzid, Tristan Houel, Ana Sofia Pereira, Marius Delcourt, Ilona Stepanov, Walid Rahal, Blanche Montagnier, Élie Kermarrec, Sofia Alvarez, Nouredine Amrani, Pénélope Guillon, Joachim Rigal, Leïla Chami, Ugo Bellanger, Hermine Plessis, Zakaria Lahcen, Mathilde Arold, Baptiste Colinart, Rania Abouzeid, Gaspard Villon, Aïcha Diallo";
		
		String[] stringList = string.split(",");
		for(int i=0; i<stringList.length; i++) {
			list.add(stringList[i]);
		}
		
		System.out.println("Appuyez sur ESPACE pour générer un mot, TAB pour quitter.");
		
		boolean running = true;
		while(running) {
			try {
				int key = System.in.read();
				NameGeneratorService generator = NameGeneratorService.getInstance();
				
				if (key == 32) {
					generateWord("naive", null, list);
				} else if (48 <= key && key <= 57) {
					String[][] arg = {{"sectionSize", String.valueOf(key - 48)}};
					generateWord("sectional", arg, list);
				} else if (key == 9) {
					running = false;
					System.out.println("Programme terminé");
				} else if (key == 10 || key == 13) continue;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 */
		
	}
	
	/**
	 public static void generateWord(String strategy, String[][] strategyArgs, NameList list) {
		try {
			NameGeneratorService generator = NameGeneratorService.getInstance();
			LocalTime t = LocalTime.now();
			String word;
			word = generator.generateName(strategy, strategyArgs, list);
			LocalTime t2 = LocalTime.now();
			Duration dt = Duration.between(t, t2);
			System.out.println("created word: " + word + " in " + dt);
		} catch (NotImplementedException | EmptyListException | BadArgsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 */

} 
