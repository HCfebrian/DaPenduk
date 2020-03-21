import 'dart:convert';
import 'dart:developer';

import 'package:http/http.dart' as http;
class Person {

  String name;
  String height;
  String mass;
  String hairColor;
  String skinColor;
  String eyeColor;
  String birthYear;
  String gender;
  String homeworld;
  List<String> films;
  List<String> species;
  List<String> vehicles;
  List<String> starships;
  String created;
  String edited;
  String url;

  Person(
  this.name,
  this.height,
  );

  Person.fromJson(Map<String, dynamic> json) {
  name = json['name'];
  height = json['height'];
  mass = json['mass'];
  hairColor = json['hair_color'];
  skinColor = json['skin_color'];
  eyeColor = json['eye_color'];
  birthYear = json['birth_year'];
  gender = json['gender'];
  homeworld = json['homeworld'];
  films = json['films'].cast<String>();
  species = json['species'].cast<String>();
  vehicles = json['vehicles'].cast<String>();
  starships = json['starships'].cast<String>();
  created = json['created'];
  edited = json['edited'];
  url = json['url'];
  }

  static Future<List<Person>> getPersons() async {
    String apiURL = "https://swapi.co/api/people/";

    var apiResult = await http.get(apiURL);
    var jsonObject = json.decode(apiResult.body);

    List<dynamic> listPerson = (jsonObject as Map<String,dynamic>)['results'];

    List<Person> persons = [];
    for(int i =0; i<listPerson.length; i++){
      persons.add(Person.fromJson(listPerson[i]));
    }

    log("banyaknya getperson" + persons.length.toString());
    return persons;
  }
}

