
// Minimum number of campsites required to reach Cn????

import java.util.Arrays;

public class Campsites {

    // Start at C1 = dist 0
    // Destination = final campsite Cn

    static class Campsite implements Comparable<Campsites.Campsite> {

        String name;
        int location;  // Distance from start (C_1 = dist 0)

        public Campsite(String name, int location) {
            this.name = name;
            this.location = location;
        }

        @Override
        public int compareTo(Campsites.Campsite other) {
            return this.location - other.location;     // We wanna sort by distances
            // or: Integer.compare(this.location, other.location)
        }
    }


        /**
         * @param campsites - All campsites in alphabetical order (not location!)
         * @param C - max. distance per day that can be travelled
         * @return - min. no. of campsites required (excluding first = start and final = dest)
         */
        public static int amountCampsitesNeeded(Campsite[] campsites, int C) {

            Arrays.sort(campsites);  // Sort campsites by location c1 <= c2 <= ... < cn

            int currentDistance = 0;   // Where we are now
            // int destination = campsites[campsites.length-1].location;
            int numCampsites = 0;     // To store how many campsites we need (minimum)

            // Start at 1 to prevent IdxOutOfBounds
            for (int i = 1; i < campsites.length; i++) {   // Find furthest campsite within currDist + C

                // If we passed the max C, we take the previous campsite
                // If there was no campsite before curr + C,
                if (campsites[i].location > currentDistance + C) {  // We passed the furthest campsite possible (which is prev)

                    Campsite camp = campsites[i-1];

                    if (camp.location == currentDistance) return -1;  // We couldn't find a campsite within C!

                    numCampsites++;   // Valid campsite found
                    currentDistance = camp.location;   // Now we are at the selected campsite
                }
            }
            return numCampsites;
        }


    }




