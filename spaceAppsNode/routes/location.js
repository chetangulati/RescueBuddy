const express = require('express');
const _ = require('lodash');

const {mongoose} = require('./../db/mongoose');
const {location} = require('./../db_models/location');

var route = express.Router();

route.get('/', (req, res, next) => {
  var state = req.query.state;
  location.findOne({'stateName': state}).select('stateName disasters contacts')
  .exec()
  .then((doc)=>{
    res.status(200).send(doc);
    console.log(doc);
  })
  .catch((err)=>{
    res.status(500).send("Server Error");
  });
});

module.exports = route;
