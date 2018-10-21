const express = require('express');
const _ = require('lodash');

const {mongoose} = require('./../db/mongoose');
const {user} = require('./../db_models/user');

var route = express.Router();

route.post('/', (req, res, next) => {
  var body = req.body;
  try {
      var data = new user(body);
      data.save().then((result)=>{
        res.status(200).send({"_id": result._id});
      }).catch((e)=>{
        res.status(400).send(e);
        console.log(e);
      });
  } catch (e) {
    res.status(500).send("Server error");
    console.log(e);
  }
});

module.exports = route;
