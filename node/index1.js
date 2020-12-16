var mysql = require('mysql');
var bodyParser = require('body-parser')

const port = 3001;
const express = require('express')

const app  = express();
app.use( bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
})); 
var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database:"userreview"
});

con.connect(function(err) {
 console.log("Connected");

  
});


app.post('/register', (req, res) => {   
    
    let data = {Fullname: req.body.Fullname,email: req.body.email, password: req.body.password};

  let sql = "INSERT INTO register SET ?";
  let query = con.query(sql, data,(err, results) => {
    if(err){
      res.setHeader('Content-Type', 'application/json');
      res.end(JSON.stringify({ "message": err,"res":"failed" }));
    }else{
      res.setHeader('Content-Type', 'application/json');
      res.end(JSON.stringify({ "message": "user added","res":"success" }));
    }
 });
      
});

app.post('/login', (req, res) => {   
    let sql = "SELECT * FROM register WHERE email='"+req.body.email+"' AND password='"+req.body.password+"'";
    console.log(req);
  let query = con.query(sql, (err, results) => {
    numRows = results.length;
    if(numRows==1){
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": "User authenticated","res":"success" }));
    }else{
        res.setHeader('Content-Type', 'application/json');
        res.end(JSON.stringify({ "message": "User Authentication Failed","res":"failed" }));
    }
  });
        
  });

  app.post('/review', (req, res) => {   
    
    var sql = "Fullname = '"+req.body.Fullname+"', description = '"+req.body.description+"' WHERE email = '"+req.body.email+"'";
    con.query(sql, function (err, result) {
     if(err){
         res.setHeader('Content-Type', 'application/json');
         res.end(JSON.stringify({ "message": err,"res":"failed" }));
       }else{
         res.setHeader('Content-Type', 'application/json');
         res.end(JSON.stringify({ "message": "user updated","res":"success" }));
       }
   });
         
   });
 
  
  app.listen(port, () => {  console.log('We are live on ' + port);});