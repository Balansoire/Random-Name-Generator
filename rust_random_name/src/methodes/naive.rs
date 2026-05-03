use std::cmp::max;
use rand;
use crate::types::{ListeNom, Nom};

pub fn genere_nom_naive(liste: &ListeNom) -> Nom {
  let mut taille_nom = 0;
  for nom in liste {
    taille_nom += nom.len() as u64;
  }
  let taille_nom = taille_nom / liste.len() as u64;
  let taille_nom = max(taille_nom, 1);
  let mut mot = String::new();
  let mut i: u64 = 0;
  while i <= taille_nom {
    let selection = rand::random_range(0..liste.len()) as usize;
    let selection = &liste[selection];
      if let Some(c) = selection.chars().nth(i as usize) {
        mot = format!("{}{}",mot, c);
        i += 1;
      } else { 
        continue;
      }
  }
  String::from(mot)
}