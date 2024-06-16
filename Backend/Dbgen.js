const Data=require("./Data/DbConnection");
const db=Data.getInstance();

async function createTable(){
    try{
        await db.query(`CREATE TABLE IF NOT EXISTS FOOD(
            Id INT AUTO_INCREMENT PRIMARY KEY,
            Email VARCHAR(250),
            FoodCategory VARCHAR(250),
            Name VARCHAR(250),
            Calories INT,
            Protein INT
        )`)


        await db.query(`CREATE TABLE IF NOT EXISTS WATER(
                Id INT AUTO_INCREMENT PRIMARY KEY,
                Email VARCHAR(250),
                Value INT
            )`)

            await db.end();


    }catch(err){
        console.log(err);
    }
}

createTable();
console.log("Baza de date este generata!");