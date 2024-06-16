const Data=require("../Data/DbConnection");
const express = require("express");
const router = express.Router();
const db=Data.getInstance();


router.post("/Water",async(req,res)=>{
    const {UserId,Value}=req.body;
    try{
        const [query]=await db.execute("INSERT INTO WATER(UserId,Value,DOC) VALUES(?,?,CURDATE())",[UserId,Value]);
        res.status(200).json({Message:"Operatia a avut succes"});
    }catch(err){
        console.log(err);
        res.status(500).json(err);
    }
})

router.get("/Water/:Id",async(req,res)=>{
    try{
        const [query]=await db.execute("SELECT SUM(VALUE) AS TOTAL_WATER FROM WATER WHERE DOC=CURDATE()");
        res.status(200).json(query)
    }catch(err){
        console.log(err);
        res.status(500).json(err);
    }
})



module.exports=router;