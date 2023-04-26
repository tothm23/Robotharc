package megoldas;

/**
 *
 * @author Paksi Norbert
 */
import java.util.ArrayList;
import java.util.Random;

public abstract class Robot {

    private String nev;
    private String szin;
    private Integer eletero;
    private int maxEletero;
    private boolean harcose;
    private Integer sebzes;

    public Robot(String nev, String szin, int eletero, boolean harcose, int sebzes) {
        this.nev = nev;
        this.szin = szin;
        this.eletero = eletero;
        this.maxEletero = eletero;
        this.harcose = harcose;
        this.sebzes = sebzes;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getSzin() {
        return szin;
    }

    public void setSzin(String szin) {
        this.szin = szin;
    }

    public int getEletero() {
        return eletero;
    }

    public void setEletero(int eletero) {
        this.eletero = eletero;
    }

    public int getMaxEletero() {
        return maxEletero;
    }

    public void setMaxEletero(int maxEletero) {
        this.maxEletero = maxEletero;
    }

    public boolean isHarcose() {
        return harcose;
    }

    public void setHarcose(boolean harcose) {
        this.harcose = harcose;
    }

    public int getSebzes() {
        return sebzes;
    }

    public void setSebzes(int sebzes) {
        this.sebzes = sebzes;
    }

    public static boolean Harcosok(Robot robot1, Robot robot2) {
        return robot1.harcose && robot2.harcose;
    }

    public static Robot Kezdorobot(Robot robot1, Robot robot2) {
        /*
        Vizsgálatok száma
        if (robot1.eletero > robot2.eletero) { // 1. vizsgálat
            return "robot2";
        } else if (robot1.eletero < robot2.eletero) { // 2. vizsgálat
            return "robot1";
        } else {
            if (robot1.sebzes > robot2.sebzes) { // 3. vizsgálat
                return "robot1";
            } else if (robot1.sebzes < robot2.sebzes) { // 4. vizsgálat
                return "robot2";
            } else {
                int szam1 = randomszam();
                int szam2 = randomszam();
                if (szam1 > szam2) {
                    return "robot1"; // 5. vizsgálat
                } else {
                    return "robot2"; // 5. vizsgálat
                }

            }
        }
         */

        if ((robot2.eletero > robot1.eletero)
                || (robot1.eletero.equals(robot2.eletero)) && robot1.sebzes > robot2.sebzes
                || (robot1.eletero.equals(robot2.eletero)) && robot1.sebzes.equals(robot2.sebzes) && Robot.randomszam(2) == 0) {
            return robot1; // 1 vagy 3 vizsgálat
        } else {
            return robot2; // 1 vagy 3 vizsgálat
        }
    }

    public static int randomszam(int max) {
        Random random = new Random();
        int x = random.nextInt(max);
        return x;
    }

    public static int veletlenEgeszSzam(int tol, int ig) {
        return (int) Math.floor(Math.random() * (ig - tol + 1) + tol);
    }

    public int sebzes() {
        return randomszam(this.sebzes + 1);
    }

    public void Tamadas(Robot szenvedo) {

        // A támadó robot
        int damage = this.sebzes(); // 4

        System.out.print("🔥 " + this.nev + " megtámadja " + szenvedo.nev + " és " + damage + " sebzést okoz");

        if (szenvedo.getEletero() - damage >= 0) {
            szenvedo.setEletero(szenvedo.getEletero() - damage); // 30-4
        } else {
            return;
        }

        System.out.println(" " + szenvedo.nev + " életereje " + szenvedo.getEletero() + " lett");

        // Minden támadás után gyógyulnak a robotok
        Gyogyulas(damage, 2);
    }

    public void Gyogyulas(int damage, int heal) {
        if (damage == this.sebzes) {
            this.setEletero(this.getEletero() + heal);  // Max 40, Aktuális 32

            System.out.println("\n🖤 " + this.nev + " maximálisat sebzett, ezért gyógyult. Új életereje: " + this.eletero + "\n");
            if (this.eletero > this.maxEletero) {
                this.eletero = this.maxEletero;
            }
        }

    }

    public static void Harc(Robot robot1, Robot robot2) {
        if (Harcosok(robot1, robot2)) {
            Robot kezdorobot = Kezdorobot(robot1, robot2);
            boolean jatek = true;
            while (jatek) {
                if (kezdorobot.equals(robot1)) {
                    robot1.Tamadas(robot2);
                    if (robot2.eletero <= 0) {
                        System.out.println("Gyöztes: " + robot1.nev);
                        jatek = false;
                    } else {
                        robot2.Tamadas(robot1);
                    }

                } else {
                    robot2.Tamadas(robot1);
                    if (robot1.eletero <= 0) {
                        System.out.println("Gyöztes: " + robot2.nev);
                        jatek = false;
                    } else {
                        robot1.Tamadas(robot2);
                    }

                }

            }

        } else {
            System.out.println("Az egyik robot nem harcos.");
            System.exit(0);
        }
    }

    /**
     * Megállapítja a jelenlegi fordulő győztesét
     *
     * @param robot1
     * @param robot2
     * @return
     */
    public static Robot Gyoztes(Robot robot1, Robot robot2) {

        if (Harcosok(robot1, robot2)) {

            Robot kezdorobot = Kezdorobot(robot1, robot2);
            boolean jatek = true;

            while (jatek) {
                if (kezdorobot.equals(robot1)) {
                    robot1.Tamadas(robot2);

                    if (robot2.getEletero() <= 0) {
                        jatek = false;
                        return robot1;
                    } else {
                        robot2.Tamadas(robot1);
                    }

                } else {
                    robot2.Tamadas(robot1);

                    if (robot1.getEletero() <= 0) {
                        jatek = false;
                        return robot2;
                    } else {
                        robot1.Tamadas(robot2);
                    }

                }

            }

        } else {
            System.out.println("Az egyik robot nem harcos.");
            System.exit(0);
        }

        return null;

    }

    public static void Robotbajnoksag(ArrayList<Robot> robotok) {

        boolean bajnoksag = true;

        for (Robot robot : robotok) {

            // Ha van akár 1 olyan robot, amelyik nem harcos
            if (!robot.isHarcose()) {

                // Akkor nem lesz bajnokság
                bajnoksag = false;
            }
        }
        // CSak akkor lehet bajnokság, ha minden robot harcos
        if (bajnoksag) {
            // A robotok akik bejutottak al elődöntőbe 5 életet kapnak
            Robot elodontosA = Gyoztes(robotok.get(0), robotok.get(1));
            elodontosA.setEletero(elodontosA.getEletero() + 5);
            System.out.println("\n🏆  Az 1. elődöntős " + elodontosA.getNev() + ", aki kap 5 életerőt, így életereje " + elodontosA.getEletero() + " lett\n");

            Robot elodontosB = Gyoztes(robotok.get(2), robotok.get(3));
            elodontosB.setEletero(elodontosB.getEletero() + 5);
            System.out.println("\n🏆 A 2. elődöntős " + elodontosB.getNev() + ", aki kap 5 életerőt, így életereje " + elodontosB.getEletero() + " lett\n");

            Robot elodontosC = Gyoztes(robotok.get(4), robotok.get(5));
            elodontosC.setEletero(elodontosC.getEletero() + 5);
            System.out.println("\n🏆 A 3. elődöntős " + elodontosC.getNev() + ", aki kap 5 életerőt, így életereje " + elodontosC.getEletero() + " lett\n");

            Robot elodontosD = Gyoztes(robotok.get(6), robotok.get(7));
            elodontosD.setEletero(elodontosD.getEletero() + 5);
            System.out.println("\n🏆 A 4. elődöntős " + elodontosD.getNev() + ", aki kap 5 életerőt, így életereje " + elodontosD.getEletero() + " lett\n");

            // A robotok akik bejutottak al döntőbe 5 életet kapnak
            Robot dontosA = Gyoztes(elodontosA, elodontosB);
            dontosA.setEletero(dontosA.getEletero() + 5);
            System.out.println("\n🏆 Az 1. döntős " + dontosA.getNev() + ", aki kap 5 életerőt, így életereje " + dontosA.getEletero() + " lett\n");

            Robot dontosB = Gyoztes(elodontosC, elodontosD);
            dontosB.setEletero(dontosB.getEletero() + 5);
            System.out.println("\n🏆 A 2. döntős " + dontosB.getNev() + ", aki kap 5 életerőt, így életereje " + dontosB.getEletero() + " lett\n");

            Robot gyoztes = Gyoztes(dontosA, dontosB);

            System.out.println("\n🏆🏆🏆🏆🏆 🏆🏆🏆🏆🏆 🏆🏆🏆🏆🏆 🏆🏆🏆🏆🏆");
            System.out.println("🏆  A bajnok nem más, mint " + gyoztes.getNev() + " !  🏆");
            System.out.println("🏆🏆🏆🏆🏆 🏆🏆🏆🏆🏆 🏆🏆🏆🏆🏆 🏆🏆🏆🏆🏆\n");
        }

    }

}
