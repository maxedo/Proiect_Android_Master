const Data=require("../Data/DbConnection");
const express = require("express");
const router = express.Router();
const db=Data.getInstance();


router.post("/Food",async(req,res)=>{
    const {Email,FoodCategory,Name,Calories,Protein}=req.body;
    try{
        const [query]=await db.execute("INSERT INTO FOOD(Email,FoodCategory,Name,Calories,Protein) VALUES(?,?,?,?,?)",[Email,FoodCategory,Name,Calories,Protein]);
        res.status(200).json({Message:"Operatia a avut succes"})
    }catch(err){
        console.log(err);
        res.status(500).json(err)
    }
})


router.get("/FoodCurrent/:Email",async(req,res)=>{
    try{
        const [query]=await db.execute("SELECT * FROM FOOD WHERE Email=?",[req.params.Id])
        res.status(200).json(query);
    }catch(err){
        console.log(err);
        res.status(500).json(err)
    }
})


router.delete("/Food/:Id",async(req,res)=>{
    try{
        const [query]=await db.execute("DELETE FROM FOOD WHERE Id=?",[req.params.Id]);
        res.status(200).json({Message:"Operatia a avut succes"});
    }catch(err){
        console.log(err);
        res.status(500).json(err)
    }
})

module.exports=router