class Orthopedist extends Doctor {
    public Orthopedist(int id, String name) {
        super(id, name);
    }

    @Override
    public String getSpeciality() {
        return "Orthopedics";
    }
}
