const {mongoose} = require('./../db/mongoose');
const {item} = require('./items');
const bcrypt = require('bcrypt');

var userSchema = mongoose.Schema({
  name:{
    type: String,
    required: true,
  },
  email:{
     type: String,
     required: true,
   },
   gender:{
     type: String,
     required: true,
     minlength: 1
   },
   age:{
     type: Number,
   },
   mbnum:{
     type: Number,
     required: true
   },
   height:{
     type:Number,
   },
   kilogram:{
     type: Number,
   },
   pass:{
     type: String,
     required: true,
   },
   family:[{
     relation:{
       type: String,
       minlength: 1
     },
     name:{
       type: String,
       minlength: 1
     },
     email:{
        type: String,
        minlength: 10
     },
     age:{
       type: Number,
       minlength: 1
     },
     height:{
       type:Number,
       minlength: 1
     },
     kilogram:{
       type: Number,
       minlength: 1
     },
     gender:{
       type: String,
       minlength: 1
     },
     mbnum:{
       type: Number,
       minlength: 10
     }
   }],
   pet:[{
     name:{
       type: String,
       minlength: 1
     },
     type:{
       type: String,
       minlength: 1
     }
   }],
   // evacuation:{
   //   // add info
   // },
   goBag:[{
     disaster:{
       type: String,
       minlength: 1
     },
     items:[{
       itemId:{
         type: mongoose.Types.ObjectId,
         ref: 'item',
         minlength: 1
       },
       quantity:{
         type:Number,
         minlength: 1
       }
     }]
   }]
});

userSchema.pre('save', (next) => {
  if(this.pass){
    var salt = bcrypt.genSaltSync(10);
    this.pass = bcrypt.hashSync(this.pass, salt);
  }
  next();
});

var user = mongoose.model('user', userSchema);
module.exports = {user};
