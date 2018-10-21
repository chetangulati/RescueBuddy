const express = require('express');
const _ = require('lodash');
const multer = require('multer');

const {mongoose} = require('./../db/mongoose');
const {user} = require('./../db_models/user');
const {disaster} = require('./../db_models/disaster');
const {location} = require('./../db_models/location');
const {item} = require('./../db_models/items');

var route = express.Router();
var upload = multer({dest: 'media/uploads', filename: function (req, file, cb) {
    cb(null, Date.now()+'.' + mime.extension(file.mimetype));
  }
});


// Post routes

route.post('/loc', (req, res, next) => {
  var body = req.body;
  try {
    var data = new location(body);
    data.save().then((result) => {
      res.status(200).send({"_id": result._id});
    }).catch((e) => {
      res.status(400).send("Bad request");
      console.log(e);
    })
  } catch (e) {
    res.status(500).send("Server Error");
    console.log(e);
  }
});

route.post('/disaster', upload.fields([{name: 'disaster_image', maxCount: 10},{name: 'disaster_background_image', maxCount: 1}]), (req, res, next) => {
  var d_img = req.files['disaster_image'];
  var b_img = req.files['disaster_background_image'];
  var body = req.body;
  body.disaster_image = d_img[0].filename;
  body.disaster_background_image = _.map(b_img, 'filename');
  try {
    body.shelter_steps = body.shelter_steps.split("#");
    body.evac_steps = body.evac_steps.split("#");

    var data = new disaster(body);
    data.save().then((result) => {
      res.status(200).send();
    }).catch((e) => {
      res.status(400).send("Bad request");
      console.log(e);
    });
  } catch (e) {
    res.status(500).send("Server Error");
    console.log(e);
  }
});

route.post('/item', (req, res, next) => {
  var body = req.body;
  try {
    var data = new item(body);
    data.save().then((result) => {
      res.status(200).send({"_id":result._id});
    }).catch((e) => {
      res.status(400).send("Bad request");
      console.log(e);
    })
  } catch (e) {
    res.status(500).send("Server Error");
  }
});

// get routes

route.get('/item', (req, res, next)=>{
  item.find().select('_id item_name item_wt item_desc item_qty_min item_qty_max item_exp_date')
  .exec()
  .then(doc=>{
    res.status(200).send(doc);
  })
  .catch(err=>{
    res.status(500).send("Server Error");
  });
});

route.get('/disaster', (req, res, next) => {
  disaster.find().exec()
  .then(doc=>{
    res.status(200).send(doc);
  }).catch((e) => {
    res.status(500).send("Server Error");
  });
});

// delete routes

route.delete('/item/:id', (req, res, next) => {
    item.findByIdAndDelete(req.params.id).exec().then((result) => {
      res.status(200).send();
    }).catch((e) => {
      res.status(400).send(e);
      console.log(e);
    })
});

module.exports = route;
