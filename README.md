# HealthSync - Διαχείριση Ιατρικών Ραντεβού

Η εφαρμογή **HealthSync** είναι μια διαδραστική εφαρμογή γραφικού περιβάλλοντος (GUI) που έχει σχεδιαστεί για τη διαχείριση ιατρικών ραντεβού. Η εφαρμογή βασίζεται στη γλώσσα προγραμματισμού **Java** και το **Java Swing** για τη δημιουργία του γραφικού περιβάλλοντος χρήστη (UI). Ενσωματώνει λειτουργικότητα για εγγραφή, σύνδεση, διαχείριση χρηστών, και προγραμματισμό ή προβολή ραντεβού. Χρησιμοποιεί μια **MySQL** βάση δεδομένων για την αποθήκευση και ανάκτηση δεδομένων.

---

## 1. Σκοπός και Λειτουργίες

Ο στόχος της εφαρμογής είναι να παρέχει ένα φιλικό προς τον χρήστη εργαλείο για τη διαχείριση ιατρικών ραντεβού, εξασφαλίζοντας:

- **Εγγραφή και σύνδεση χρηστών.**
- **Προγραμματισμό ραντεβού** με βάση τη διαθεσιμότητα γιατρών και ειδικοτήτων.
- **Διαχείριση ραντεβού**:
  - Προβολή προγραμματισμένων ραντεβού.
  - Διαγραφή ραντεβού.
  - Πρόταση εναλλακτικών ημερομηνιών και ωρών.
- **Διαχείριση γιατρών**: Αποθήκευση γιατρών και ειδικοτήτων.

---

## 2. Κύρια Στοιχεία και Δομή

### Αρχιτεκτονική

Η εφαρμογή βασίζεται σε τρία κύρια επίπεδα:

1. **Γραφικό Περιβάλλον Χρήστη (UI)**:

   - Παρέχει οθόνες για εγγραφή, σύνδεση, προβολή και προγραμματισμό ραντεβού.
   - Χρησιμοποιεί το Java Swing για την κατασκευή παραθύρων, πλαισίων και κουμπιών.

2. **Διαχείριση Επιχειρησιακής Λογικής**:

   - **`UserManager`**:
     - Διαχείριση χρηστών: εγγραφή, σύνδεση, ανάκτηση ID.
   - **`DoctorManager`**:
     - Αρχικοποίηση γιατρών και ειδικοτήτων.
   - **`AppointmentManager`**:
     - Λειτουργίες για τον προγραμματισμό, την προβολή και τη διαγραφή ραντεβού.
     - Έλεγχοι για την εγκυρότητα ημερομηνιών και ωρών.
     - Πρόταση εναλλακτικών επιλογών.

3. **Επικοινωνία με Βάση Δεδομένων**:
   - **`DatabaseManager`**:
     - Ρύθμιση σύνδεσης με τη MySQL βάση δεδομένων.
     - Δημιουργία πινάκων και αρχικοποίηση δεδομένων.

---

### Γραφικό Περιβάλλον Χρήστη

#### 1. Οθόνη Σύνδεσης

- Είσοδος χρήστη με όνομα χρήστη και κωδικό πρόσβασης.
- Κουμπί για μετάβαση στην οθόνη εγγραφής.

#### 2. Οθόνη Εγγραφής

- Φόρμα για δημιουργία νέου λογαριασμού.
- Επαλήθευση κωδικού πρόσβασης.

#### 3. Κεντρικό Μενού

- Υποδοχή χρήστη με μήνυμα καλωσορίσματος.
- Επιλογές:
  - **Προγραμματισμός Ραντεβού**.
  - **Προβολή Ραντεβού**.
  - **Έξοδος**.

#### 4. Οθόνη Προγραμματισμού Ραντεβού

- Επιλογή ειδικότητας γιατρού.
- Καθορισμός ημερομηνίας και ώρας.
- Αυτόματος έλεγχος εγκυρότητας ημερομηνίας/ώρας.
- Πρόταση εναλλακτικών επιλογών αν το slot δεν είναι διαθέσιμο.

#### 5. Οθόνη Προβολής Ραντεβού

- Λίστα με τα προγραμματισμένα ραντεβού του χρήστη.
- Δυνατότητα διαγραφής επιλεγμένου ραντεβού.

---

## 3. Διαχείριση Βάσης Δεδομένων

### Πίνακες

1. **`users`**

   - **Σκοπός**: Αποθήκευση στοιχείων χρηστών.
   - **Πεδία**:
     - `id` (INT): Πρωτεύον κλειδί.
     - `username` (VARCHAR): Όνομα χρήστη (μοναδικό).
     - `password` (VARCHAR): Κωδικός πρόσβασης.

2. **`doctors`**

   - **Σκοπός**: Αποθήκευση γιατρών και ειδικοτήτων.
   - **Πεδία**:
     - `id` (INT): Πρωτεύον κλειδί.
     - `name` (VARCHAR): Όνομα γιατρού.
     - `specialty` (VARCHAR): Ειδικότητα.

3. **`appointments`**
   - **Σκοπός**: Αποθήκευση προγραμματισμένων ραντεβού.
   - **Πεδία**:
     - `id` (INT): Πρωτεύον κλειδί.
     - `user_id` (INT): Αναφορά στον χρήστη.
     - `doctor_id` (INT): Αναφορά στον γιατρό.
     - `date` (DATE): Ημερομηνία.
     - `time` (TIME): Ώρα.

---

## 4. Σημαντική Λειτουργικότητα

### Προγραμματισμός Ραντεβού

- Έλεγχος για:
  - Διαθεσιμότητα ώρας.
  - Ειδικότητα γιατρού.
  - Εγκυρότητα ημερομηνίας (όχι Σαββατοκύριακα, ώρες λειτουργίας).
  - Ανάθεση στον λιγότερο απασχολημένο γιατρό.

### Εναλλακτικές Ώρες

- Δυναμική δημιουργία χρονικών διαστημάτων (09:00–17:00).
- Εμφάνιση προτάσεων αν δεν υπάρχει διαθέσιμο slot.

### Αρχικοποίηση Γιατρών

- Δημιουργία λίστας γιατρών κατά την εκκίνηση της εφαρμογής.

### Προβολή Ραντεβού

- Λήψη δεδομένων από τη βάση και εμφάνιση σε λίστα.
- Διαγραφή επιλεγμένων ραντεβού.

---

## 5. Τεχνικές Λεπτομέρειες

- **Γλώσσα**: Java.
- **Βιβλιοθήκες**:
  - JDBC για τη διαχείριση βάσης δεδομένων.
  - Swing για το γραφικό περιβάλλον.
- **Βάση Δεδομένων**:
  - MySQL.
  - Πίνακες με σχέσεις `Foreign Key`.

---

# Οδηγίες Χρήσης της Εφαρμογής HealthSync

Η εφαρμογή **HealthSync** προσφέρει ένα φιλικό γραφικό περιβάλλον για τη διαχείριση ιατρικών ραντεβού. Ακολουθούν οι οδηγίες για την αποτελεσματική χρήση της εφαρμογής.

---

## 1. Εκκίνηση της Εφαρμογής

1. Εκτελέστε το αρχείο `HealthSync` από το περιβάλλον ανάπτυξης ή τη γραμμή εντολών.
2. Με την εκκίνηση, θα εμφανιστεί η **Οθόνη Σύνδεσης**.

---

## 2. Σύνδεση Χρήστη

1. Στην **Οθόνη Σύνδεσης**:
   - Συμπληρώστε το **Όνομα Χρήστη** και τον **Κωδικό** σας.
   - Πατήστε το κουμπί **"Σύνδεση"**.
2. Εάν τα στοιχεία σας είναι σωστά, θα μεταφερθείτε στο **Κεντρικό Μενού**.
3. Εάν τα στοιχεία είναι λανθασμένα:
   - Θα εμφανιστεί μήνυμα σφάλματος.
   - Ελέγξτε τα στοιχεία σας και δοκιμάστε ξανά.

---

## 3. Εγγραφή Νέου Χρήστη

1. Από την **Οθόνη Σύνδεσης**, πατήστε το κουμπί **"Εγγραφή"**.
2. Στην **Οθόνη Εγγραφής**:
   - Συμπληρώστε το **Όνομα Χρήστη**, τον **Κωδικό** και την **Επιβεβαίωση Κωδικού**.
   - Πατήστε το κουμπί **"Εγγραφή"**.
3. Εάν η εγγραφή είναι επιτυχής:
   - Θα εμφανιστεί μήνυμα επιτυχίας και θα επιστρέψετε στην **Οθόνη Σύνδεσης**.
4. Εάν υπάρχει σφάλμα (π.χ., ήδη υπάρχον όνομα χρήστη):
   - Θα εμφανιστεί μήνυμα σφάλματος.
   - Διορθώστε τα στοιχεία σας και δοκιμάστε ξανά.

---

## 4. Κεντρικό Μενού

Αφού συνδεθείτε, θα μεταφερθείτε στο **Κεντρικό Μενού**. Εδώ μπορείτε να:

1. **Προγραμματίσετε Ραντεβού**.
2. **Προβάλετε Προγραμματισμένα Ραντεβού**.
3. **Έξοδος** από την εφαρμογή.

---

## 5. Προγραμματισμός Ραντεβού

1. Πατήστε το κουμπί **"Προγραμματισμός Ραντεβού"**.
2. Στην **Οθόνη Προγραμματισμού Ραντεβού**:
   - Επιλέξτε την **Ειδικότητα** του γιατρού.
   - Συμπληρώστε την **Ημερομηνία** και την **Ώρα**.
   - Πατήστε **"Κλείσε Ραντεβού"**.
3. **Έλεγχοι και Σφάλματα**:
   - Εάν η ημερομηνία είναι Σαββατοκύριακο, θα σας ζητηθεί να επιλέξετε καθημερινή και θα σας προταθούν εναλλακτικές.
   - Εάν η ώρα είναι εκτός λειτουργίας (09:00–17:00), θα σας προταθούν εναλλακτικές.
   - Εάν το slot είναι κατειλημμένο, θα εμφανιστούν διαθέσιμες εναλλακτικές ώρες.
4. Εάν το ραντεβού προγραμματιστεί με επιτυχία, θα εμφανιστεί μήνυμα επιβεβαίωσης.
5. Εάν υπάρχει σφάλμα :
   - Θα εμφανιστεί μήνυμα σφάλματος.

---

## 6. Προβολή Ραντεβού

1. Πατήστε το κουμπί **"Προβολή Ραντεβού"**.
2. Στην οθόνη προβολής:
   - Θα εμφανιστεί λίστα με τα προγραμματισμένα ραντεβού σας.
   - Κάθε ραντεβού περιλαμβάνει: ημερομηνία, ώρα, όνομα γιατρού και ειδικότητα.
3. Επιλέξτε ένα ραντεβού και πατήστε **"Διαγραφή Ραντεβού"** αν θέλετε να το ακυρώσετε.

---

## 7. Έξοδος

1. Από το **Κεντρικό Μενού**, πατήστε το κουμπί **"Έξοδος"**.
2. Η εφαρμογή θα τερματιστεί.

---

## 8. Παράθυρο Πρότασης Διαθέσιμων Ημερομηνιών και Ωρών

Το παράθυρο αυτό εμφανίζεται όταν το slot που επιλέξατε για το ραντεβού δεν είναι διαθέσιμο ή όταν η ημερομηνία/ώρα που δώσατε δεν είναι έγκυρη. Παρέχει εναλλακτικές ημερομηνίες και ώρες, επιτρέποντας στον χρήστη να επιλέξει την επόμενη καλύτερη επιλογή.

### Περιγραφή Παράθυρου

- **Τίτλος Παράθυρου**: "Διαθέσιμες Ώρες"
- **Περιεχόμενο**:
  - Λίστα με διαθέσιμες ώρες για την ημερομηνία που επιλέξατε.
  - Ενημερωτικό μήνυμα με τη μορφή:
    ```plaintext
    Διαθέσιμες ώρες για την [Ημερομηνία]:
    1. 09:00
    2. 10:00
    3. 11:00
    ...
    ```
  - Επιλογές:
    - **"Κλείσιμο Παραθύρου"**: Τερματίζει την πρόταση διαθέσιμων επιλογών.
    - **"Επόμενη Ημέρα"**: Εμφανίζει διαθέσιμες ώρες για την επόμενη εργάσιμη ημέρα.
    - **"Επιλογή Ώρας"**: Επιτρέπει την επιλογή μιας συγκεκριμένης ώρας από τη λίστα.

### Ροή Χρήσης

1. **Εμφάνιση Παράθυρου**:

   - Το παράθυρο εμφανίζεται αυτόματα αν:
     - Η ημερομηνία είναι Σαββατοκύριακο.
     - Η ώρα είναι εκτός λειτουργικών ορίων (09:00–17:00).
     - Το slot για την ημερομηνία και ώρα που επιλέξατε είναι κατειλημμένο.

2. **Επιλογές Χρήστη**:

   - **Κλείσιμο Παραθύρου**:
     - Τερματίζει τη διαδικασία επιλογής ραντεβού.
   - **Επόμενη Ημέρα**:
     - Το σύστημα αναζητά διαθέσιμες ώρες για την επόμενη εργάσιμη ημέρα.
     - Παραλείπει αυτόματα τα Σαββατοκύριακα.
   - **Επιλογή Ώρας**:
     - Παρουσιάζει λίστα διαθέσιμων ωρών.
     - Ο χρήστης επιλέγει μια ώρα, και το ραντεβού προγραμματίζεται αυτόματα για αυτή την επιλογή.

3. **Επιτυχής Προγραμματισμός**:
   - Με την επιλογή μιας διαθέσιμης ώρας, το σύστημα:
     - Προγραμματίζει το ραντεβού.
     - Εμφανίζει μήνυμα επιβεβαίωσης.

---

## 9. Σημειώσεις και Συμβουλές

- **Διαθεσιμότητα**: Οι ώρες λειτουργίας της κλινικής είναι 09:00–17:00. Προγραμματίστε τα ραντεβού σας εντός αυτού του διαστήματος.
- **Αλλαγή Ραντεβού**: Εάν δεν βρείτε διαθέσιμη ώρα, χρησιμοποιήστε την επιλογή προτεινόμενων ωρών.
- **Σαββατοκύριακα**: Η κλινική δεν λειτουργεί τα Σαββατοκύριακα.
- **Ενημερώσεις**: Ελέγξτε τα μηνύματα επιβεβαίωσης κατά την προγραμματισμό ή την ακύρωση ενός ραντεβού.

---

Με αυτές τις οδηγίες, μπορείτε να χρησιμοποιήσετε την εφαρμογή **HealthSync** για να οργανώσετε τα ιατρικά σας ραντεβού αποτελεσματικά και χωρίς προβλήματα!

---
# Βήματα για Χρήση της Εφαρμογής:
  ## Ρύθμιση του MySQL Server
   ### Ο χρήστης πρέπει να:

    - Εγκαταστήσει τον MySQL Server και το MySQL Workbench.
    - Δημιουργήσει έναν χρήστη (π.χ., root) με τον ίδιο κωδικό που έχεις ορίσει στον DatabaseManager.java. Αν ο κωδικός διαφέρει,     θα πρέπει να τροποποιηθεί η γραμμή σύνδεσης στη μέθοδο getConnection().
  ## Δημιουργία της Βάσης Δεδομένων
   ### Ο χρήστης πρέπει να:

   - Συνδεθεί στον MySQL Server μέσω του Workbench ή του command line.
   - Εκτελέσει τις εντολές:
     
     sql
    - CREATE DATABASE HealthSyncDB;
    - USE HealthSyncDB;

Αν ο κωδικός πρόσβασης ή ο χρήστης MySQL του άλλου χρήστη είναι διαφορετικός, θα πρέπει να τροποποιήσει το αρχείο DatabaseManager.java για να ταιριάζει με τα στοιχεία του. Παράδειγμα:

  java
    String url = "jdbc:mysql://localhost:3306/HealthSyncDB";
    String user = "root"; // Αντί για 'root', βάλτε username σας
    String password = "Nick2004"; // Αντί για 'Nick2004', βάλτε τον κωδικό σας

Όταν η εφαρμογή εκκινήσει, θα δημιουργήσει αυτόματα τους πίνακες στη βάση δεδομένων.
---

# Οδηγίες Μεταγλώττισης και Εκτέλεσης της Εφαρμογής HealthSync

Ακολουθήστε τα παρακάτω βήματα για να μεταγλωττίσετε και να εκτελέσετε την εφαρμογή `HealthSync` χρησιμοποιώντας Maven.

---

## Βήμα 1: Καθαρισμός του Project

Πριν προχωρήσετε στη μεταγλώττιση, πρέπει να καθαρίσετε τα προηγούμενα build αρχεία. Αυτό διασφαλίζει ότι το έργο σας είναι έτοιμο για μια νέα, καθαρή μεταγλώττιση.

Εκτελέστε την παρακάτω εντολή:

```bash
mvn clean
```

- **Τι κάνει;**
  Καθαρίζει τον φάκελο `target/` όπου αποθηκεύονται τα παραγόμενα αρχεία.
- **Γιατί είναι σημαντικό;**
  Αποφεύγονται προβλήματα που μπορεί να προκύψουν από παλιά αρχεία.

---

## Βήμα 2: Δημιουργία του Εκτελέσιμου `.jar`

Αφού καθαρίσετε το project, προχωρήστε στη μεταγλώττιση και δημιουργία του εκτελέσιμου `.jar`. Εκτελέστε την παρακάτω εντολή:

```bash
mvn package
```

- **Τι κάνει;**
  Δημιουργεί το εκτελέσιμο αρχείο `.jar` και το αποθηκεύει στον φάκελο `target/`.
- **Πού βρίσκεται το αρχείο;**
  Το αρχείο βρίσκεται στη διαδρομή:
  ```
  target/HealthSync-1.0-SNAPSHOT.jar
  ```

**Προαιρετικό**: Αν έχετε προσθέσει το Maven Shade Plugin στο `pom.xml`, η εντολή θα δημιουργήσει ένα "fat JAR" που περιλαμβάνει όλες τις εξαρτήσεις:

```bash
mvn package
```

- Το αρχείο που παράγεται θα είναι:
  ```
  target/HealthSync-1.0-SNAPSHOT-shaded.jar
  ```

---

## Βήμα 3: Εκτέλεση της Εφαρμογής

Για να εκτελέσετε την εφαρμογή, χρησιμοποιήστε την εντολή `java -jar`.

### Εκτέλεση του `.jar` χωρίς εξαρτήσεις:

```bash
java -jar target/HealthSync-1.0-SNAPSHOT.jar
```

### Εκτέλεση του "fat JAR" (αν χρησιμοποιήσατε το Maven Shade Plugin):

```bash
java -jar target/HealthSync-1.0-SNAPSHOT-shaded.jar
```

- **Τι κάνει;**
  Εκκινεί την εφαρμογή σας. Το πρόγραμμα `HealthSync` θα ανοίξει ένα γραφικό περιβάλλον (GUI) και θα συνδεθεί στη βάση δεδομένων MySQL.

---

## Τι να κάνετε αν υπάρχουν προβλήματα:

### 1. Η βάση δεδομένων δεν συνδέεται:

- Ελέγξτε αν ο MySQL Server είναι ενεργός.
- Επιβεβαιώστε ότι τα στοιχεία σύνδεσης είναι σωστά στο αρχείο `DatabaseManager.java`:
  ```java
  private static final String URL = "jdbc:mysql://localhost:3306/HealthSyncDB";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "Nick2004";
  ```

### 2. Παρουσιάζονται εξαιρέσεις κατά την εκτέλεση:

- Ελέγξτε τα μηνύματα που εμφανίζονται στο τερματικό για λεπτομέρειες.
- Βεβαιωθείτε ότι η βάση δεδομένων περιέχει τους σωστούς πίνακες (π.χ., `users`, `doctors`, `appointments`).

---

# UML DIAGRAM
[View UML Diagram](https://drive.google.com/file/d/1JsVAf4zzTBa1xt9onBuABBI-qdew-vko/view?usp=sharing) 
