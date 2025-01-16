class Cardiologist extends Doctor {
    public Cardiologist(int id, String name) {
        super(id, name);
    }

    @Override
    public String getSpeciality() {
        return "Cardiology";
    }
}
