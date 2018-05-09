package com.github.muravyova.flyweightsample.flyweight;

import com.github.muravyova.flyweightsample.client.ListItem;

import java.util.HashMap;
import java.util.Map;

public class ParticleFactory {

    private final Map<String, Particle> mParticles;

    public ParticleFactory() {
        mParticles = new HashMap<>();
    }

    public Particle getParticle(ListItem item){
        Particle particle = mParticles.get(item.getId());

        if (particle == null){
            switch (item.getType()){
                case POINT:
                    particle = new PointParticle(item.getColor(), item.getSize());
                    break;
                case SQUARE:
                    particle = new SquareParticle(item.getColor(), item.getSize());
                    break;
                default:
                    throw new RuntimeException("Неизвестный type");
            }
            mParticles.put(item.getId(), particle);
        }

        return particle;
    }
}
