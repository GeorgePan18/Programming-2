class Dermatologist extends Doctor {
    public Dermatologist(int id, String name) {
        super(id, name);
    }

    @Override
    public String getSpeciality() {
        return "Dermatology";
    }
}