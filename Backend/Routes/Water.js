const Data=require("../Data/DbConnection");
const express = require("express");
const router = express.Router();
const db=Data.getInstance();


router.post("/Water",async(req,res)=>{
    const {Email,Value}=req.body;
    try{
        const [query]=await db.execute("INSERT INTO WATER(Email,Value) VALUES(?,?)",[Email,Value]);
        res.status(200).json({Message:"Operatia a avut succes"});
    }catch(err){
        console.log(err);
        res.status(500).json(err);
    }
})

router.get("/Water/:Email",async(req,res)=>{
    try{
        const [query]=await db.execute("SELECT SUM(VALUE) AS TOTAL_WATER FROM WATER ");
        res.status(200).json(query)
    }catch(err){
        console.log(err);
        res.status(500).json(err);
    }
})



module.exports=router;