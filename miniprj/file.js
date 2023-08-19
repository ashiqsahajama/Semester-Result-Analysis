function subject(code, batch, sem, sec, cat) {
    console.log(code);
    console.log(batch);
    console.log(sem);
    console.log(sec);
    console.log(cat);

    document.cookie = "subcode="+code;
    document.cookie = "batch="+batch;
    document.cookie = "sem="+sem;
    document.cookie = "sec="+sec;
    document.cookie = "cat="+cat;

    window.location.replace("temp");
}

function semsubject(code,batch,sem,sec){
    console.log(code);
    console.log(batch);
    console.log(sem);
    console.log(sec);

    document.cookie = "subcode="+code;
    document.cookie = "batch="+batch;
    document.cookie = "sem="+sem;
    document.cookie = "sec="+sec;

    window.location.replace("semsub");
}

function studsem(code,roll,batch,sem,sec){
    console.log(code);
    console.log(roll);
    console.log(batch);
    console.log(sem);
    console.log(sec);

    document.cookie = "subcode="+code;
    document.cookie = "roll="+roll;
    document.cookie = "batch="+batch;
    document.cookie = "sem="+sem;
    document.cookie = "sec="+sec;

    window.location.replace("studentsem");
}

function stusubject(code,batch,sem,sec,roll){
    console.log(code);
    console.log(batch);
    console.log(sem);
    console.log(sec);
    console.log(roll);

    document.cookie = "subcode="+code;
    document.cookie = "batch="+batch;
    document.cookie = "sem="+sem;
    document.cookie = "sec="+sec;
    document.cookie = "roll="+roll;

    window.location.replace("studcat");
    
}