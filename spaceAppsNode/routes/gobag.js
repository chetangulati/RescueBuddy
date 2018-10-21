const {mongoose} = require('./../db/mongoose');
const {disaster} = require('./../db_models/disaster');
const {item} = require('./../db_models/items');
const {user} = require('./../db_models/user');

const express = require('express');

var route = express.Router();

route.post('/', async (req, res, next) => {
  var body = req.body;
  try {
    var itemdata = await item.find();
    var userData = await user.findById(req.body.id).select('kilogram height age');

    var bmi = (userData.height*1000)/(userData.kilogram*userData.kilogram);
    var perc = 0.15;
    if((bmi < 18.5 && bmi > 24.9) && (userData.age > 15 && userData.age < 45)) perc = 0.2;
    var data = {
      "items": itemdata,
      "weight": userData.kilogram*perc
    }
    res.status(200).send(data);

  } catch (e) {
    res.status(500).send(e);
    console.log(e);
  }
});

module.exports = route;
