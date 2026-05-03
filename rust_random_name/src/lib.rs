use methodes::naive::genere_nom_naive;
use types::{ListeNom, Nom};

use crate::methodes::markov::genere_nom_markov_direct;

pub mod methodes;
pub mod types;


pub enum Methodes {
  Naive,
  Markov(usize)
}

pub fn generer_liste(liste_brute: &str) -> ListeNom {
  Vec::from_iter(liste_brute.split(", ").map(|s| {String::from(s)}))
}

pub fn genere_nom(liste: &ListeNom, methode: Methodes) -> Nom {
  match methode {
    Methodes::Naive => genere_nom_naive(liste),
    Methodes::Markov(n) => genere_nom_markov_direct(&liste, n)
  }
}
