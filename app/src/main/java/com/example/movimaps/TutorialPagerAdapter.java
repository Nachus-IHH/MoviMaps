package com.example.movimaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TutorialPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 4;

    public TutorialPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return TutorialFragment.newInstance(
                        "Bienvenido a OSM Maps",
                        "Explora el mundo con mapas detallados y funciones avanzadas de navegación.",
                        R.drawable.icon_home
                );
            case 1:
                return TutorialFragment.newInstance(
                        "Agrega Pines",
                        "Marca tus lugares favoritos y guarda ubicaciones importantes con pines personalizados.",
                        R.drawable.ic_location_red
                );
            case 2:
                return TutorialFragment.newInstance(
                        "Crea Rutas",
                        "Calcula rutas optimizadas entre diferentes puntos y navega con facilidad.",
                        R.drawable.ic_route
                );
            case 3:
                return TutorialFragment.newInstance(
                        "¡Comienza a Explorar!",
                        "Todo está listo. Comienza a usar la aplicación y descubre nuevos lugares.",
                        R.drawable.ic_explore
                );
            default:
                return TutorialFragment.newInstance("", "", R.drawable.icon_home);
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}

