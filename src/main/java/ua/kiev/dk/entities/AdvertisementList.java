package ua.kiev.dk.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement(name = "advertisements")
public class AdvertisementList {



        @XmlElement(name = "advertisement")
        private List<Advertisement> advList = new ArrayList<Advertisement>();

        public void addAdv(Advertisement adv) {
            advList.add(adv);
        }

        public AdvertisementList() {
        }

        public List<Advertisement> getAdvList() {
            return advList;
        }
    }

