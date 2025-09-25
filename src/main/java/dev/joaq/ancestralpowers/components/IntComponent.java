package dev.joaq.ancestralpowers.components;


import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.Component;

public interface IntComponent extends Component {
    int getValue();
    void setValue(int value);
}

    class RandomIntComponent implements IntComponent {
        private int value = 0;

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public void writeData(WriteView writeView) {

            writeView.putInt("random_int",value);
        }

        @Override
        public void readData(ReadView readView) {

            this.value = readView.getInt("random_int", value);
        }
    }


