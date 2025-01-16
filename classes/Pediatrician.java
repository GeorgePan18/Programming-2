class Pediatrician extends Doctor {
    public Pediatrician(int id, String name) {
        super(id, name);
    }

    @Override
    public String getSpeciality() {
        return "Pediatrics";
    }
}