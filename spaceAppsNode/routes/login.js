const express = require('express');
const _ = require('lodash');

const {mongoose} = require('./../db/mongoose');
const {user} = require('./../db_models/user');

var route = express.Router();

route.post('/', (req, res, next) => {
  var body = req.body;
  try {
    user.findOne({'email': body.email}).exec()
    .then(async (doc) => {
      if (doc.pass == body.pass) {
        res.send(doc);
      }
      else {
        res.status(400).send({"err": "Unable to authenticate"});
      }
    })
    .catch((err) => {
      res.status(400).send(err);
      console.log(err);
    });
  } catch (e) {
    res.status(400).send(e);
    console.log(e);
  }
});

module.exports = route;
