package dev.joaq.ancestralpowers.components;


import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.Component;

public interface PlayerTraits extends Component {
    String getMovementPower();
    void setMovementPower(String power);

    String getMainPower();
    void setMainPower(String power);

    String getIntelligence();
    void setIntelligence(String intelligence);
}

    class PlayerTraitsComponent implements PlayerTraits {
        private String movement;
        private String main;
        private String intelligence;

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

        }

        @Override
        public void readData(ReadView readView) {
            this.movement = readView.getString("movement", movement);
            this.main = readView.getString("main", main);
            this.intelligence = readView.getString("intelligence", intelligence);
        }
    }


