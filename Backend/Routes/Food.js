const Data=require("../Data/DbConnection");
const express = require("express");
const router = express.Router();
const db=Data.getInstance();


router.post("/Food",async(req,res)=>{
    const {Email,FoodCategory,Name,Calories,Protein}=req.body;
    try{
        console.log(Email+" "+FoodCategory+" "+Name+" "+Calories+" "+Protein)
        const [query]=await db.execute("INSERT INTO FOOD(Email,FoodCategory,Name,Calories,Protein) VALUES(?,?,?,?,?)",[Email,FoodCategory,Name,Calories,Protein]);
        res.status(200).json({Message:"Operatia a avut succes"})
    }catch(err){
        console.log(err);
        res.status(500).json(err)
    }
})


router.get("/FoodCurrent/:Email", async (req, res) => {
    try {
        const [query] = await db.execute("SELECT * FROM FOOD WHERE Email = ?", [req.params.Email]);
        res.status(200).json(query);
    } catch (err) {
        console.log(err);
        res.status(500).json(err);
    }
});

router.get("/FoodCurrentValue/:Email", async (req, res) => {
    try {
        const [query] = await db.execute("SELECT SUM(Calories) as current_calories FROM FOOD WHERE Email = ?", [req.params.Email]);
        if (query.length > 0) {
            const currentCalories = query[0].current_calories;
            res.status(200).send(currentCalories.toString()); // Send as plain text
        } else {
            res.status(404).send("0"); // If no results, send 0
        }
    } catch (err) {
        console.log(err);
        res.status(500).json(err);
    }
});


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