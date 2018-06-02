import java.util.HashSet;
import java.util.Set;

public class Teacher {

        private long id;
        private String name;
        private String surname;

        private Set<SchoolClass> charges;


        public Teacher(String name, String surname) {
            this();
            this.name = name;
            this.surname = surname;

        }

        public Teacher() {
            super();
        }


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }


        public Set<SchoolClass> getCharges() {
            return charges;
        }

        public void setCharges(Set<SchoolClass> lectures) {
            if (charges == null) {
                charges=new HashSet<SchoolClass>();
            }
            this.charges = lectures;
        }

        public void addCharges(SchoolClass schoolClass) {
            if (charges == null) {
                charges=new HashSet<SchoolClass>();
            }
            charges.add(schoolClass);
        }

        @Override
        public String toString() {
            return "Teacher [id=" + id + ", name=" + name + ", surname=" + surname +
                    ", charges=" + charges + "]";
        }

    }



