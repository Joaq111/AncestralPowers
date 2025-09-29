package dev.joaq.ancestralpowers.components;


import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.Component;

public interface PlayerTraits extends Component {
    Float getStamina();
    void setStamina(Float stamina);
    Boolean getActPower_main();
    void setActPower_main(Boolean actPower_main);

    Boolean getActPower_secondary();
    void setActPower_secondary(Boolean actPower_secondary);

    String getMovementPower();
    void setMovementPower(String power);

    String getMainPower();
    void setMainPower(String power);

    String getIntelligence();
    void setIntelligence(String intelligence);
}

    class PlayerTraitsComponent implements PlayerTraits {
        private boolean actPower_main;
        private boolean actPower_secondary;

        private Float stamina;

        private String movement;
        private String main;
        private String intelligence;

        @Override
        public Float getStamina() {
            return stamina;
        }

        @Override
        public void setStamina(Float stamina) {
            this.stamina = stamina;
        }

        @Override
        public Boolean getActPower_main() {
            return actPower_main;

        }

        @Override
        public void setActPower_main(Boolean actPower_main) {
            this.actPower_main = actPower_main;

        }

        @Override
        public Boolean getActPower_secondary() {
            return actPower_secondary;
        }

        @Override
        public void setActPower_secondary(Boolean actPower_secondary) {
            this.actPower_secondary = actPower_secondary;

        }

        @Override
        public String getMovementPower() {
            return movement;
        }

        @Override
        public void setMovementPower(String power) {
            this.movement = power;
        }

        @Override
        public String getMainPower() {
            return main;
        }

        @Override
        public void setMainPower(String power) {
            this.main = power;
        }

        @Override
        public String getIntelligence() {
            return intelligence;
        }

        @Override
        public void setIntelligence(String intelligence) {
            this.intelligence = intelligence;
        }


        @Override
        public void writeData(WriteView writeView) {
            writeView.putString("movement",movement);
            writeView.putString("main",main);
            writeView.putString("intelligence",intelligence);

            writeView.putBoolean("actPower_main",actPower_main);
            writeView.putBoolean("actPower_secondary",actPower_secondary);

            writeView.putFloat("stamina",stamina);

        }

        @Override
        public void readData(ReadView readView) {
            this.movement = readView.getString("movement", movement);
            this.main = readView.getString("main", main);
            this.intelligence = readView.getString("intelligence", intelligence);
            this.actPower_main = readView.getBoolean("actPower_main", actPower_main);
            this.actPower_secondary = readView.getBoolean("actPower_secondary", actPower_secondary);
            if (readView.contains("stamina")) {
                this.stamina = readView.getFloat("stamina", 100f); // garante valor default se ausente
            } else if (this.stamina == null) {
                this.stamina = 100f; // valor padrão caso ainda seja null
            }
        }

        }


