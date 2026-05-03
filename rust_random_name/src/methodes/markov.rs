use crate::types::{ListeNom, Nom};
use std::collections::HashMap;

type Distribution = HashMap<String, (Vec<(char, usize)>, usize)>;


pub fn genere_nom_markov_direct(liste: &ListeNom, n: usize) -> Nom {
  let distro = calcul_distro(liste, n);
  genere_nom_markov(&distro, n)
}

pub fn calcul_distro(liste: &ListeNom, n: usize) -> Distribution {
  let mut distro: Distribution = HashMap::new();
  for nom in liste {
    let chars: Vec<char> = nom.chars().collect();
    let len = chars.len();
    for i in 0..=len {
      let tk = if i < n {
        "_".repeat(n - i) + &chars[..i].iter().collect::<String>()
      } else {
        chars[i - n..i].iter().collect::<String>()
      };

      let c = if i < len {
        chars[i]
      } else {
        '*'
      };
      inserer_distro(tk, c, &mut distro);
    }
  }
  for (_, (distro_tk, _)) in &mut distro {
    distro_tk.sort_by(|a, b| a.1.cmp(&b.1))
  }
  distro
}

fn inserer_distro(tk: String, c: char, distro: &mut Distribution) {
  let distro_tk = distro.entry(tk).or_default();

  let mut trouve = false;
  for (c_temp, poids) in &mut distro_tk.0 {
    if *c_temp == c {
      *poids += 1;
      trouve = true;
    }
  }
  if !trouve {
    distro_tk.0.push((c, 1));
  }
  distro_tk.1 += 1;
}

pub fn genere_nom_markov(distro: &Distribution, n: usize) -> Nom {
  let mut nom: Nom = "_".repeat(n);
  for _ in 0..n {
    let c = genere_char(&nom, distro);
    if c == '*' {
      nom = nom.replace("_", "");
      break;
    } else {
      nom = nom.replacen("_", "", 1);
      nom.push(c);
    }
  }
  loop {
    let tk = last_n_chars(&nom, n);
    let c = genere_char(&tk, distro);
    if c == '*' {
      break;
    } else {
      nom.push(c);
    }
  }
  nom
}

fn last_n_chars(s: &str, n: usize) -> String {
  let count = s.chars().count();
  if count >= n {
    s.chars().skip(count - n).collect()
  } else {
    "_".repeat(n - count) + &s
  }
}

fn genere_char(tk: &str, distro: &Distribution) -> char {
  let (distro_tk, poids_total) = distro.get(tk).expect(&format!("Le token recherché devrait être dans la distribution ({})", tk));
  let mut selection = rand::random_range(0..*poids_total);
  for (c, poids) in distro_tk{
    if selection < *poids {
      return *c;
    }
    selection -= *poids;
  }
  unreachable!("Erreur dans la selection")
}