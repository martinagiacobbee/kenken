package backtracking;

import model.Cell;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class Problema<P,S> {

    protected abstract boolean assegnabile(P p, S s);

    protected abstract void assegna(P ps, S s);

    protected abstract void deassegna(P ps);

    protected abstract void scriviSoluzione();

    /*private P prossimoPuntoDiScelta(List<P> ps, P p) {
        if (esisteSoluzione(p)) throw new NoSuchElementException();
        int i = ps.indexOf(p);
        System.out.println("index of "+p+": "+i);
        if(i+1 >= ps.size()) System.out.println("Nessuna scelta possibile per "+p);
        System.out.println("Prossimo punto di scelta: "+ps.get(i+1));
        return ps.get(i + 1);
    }//prossimoPuntoDiScelta*/

    protected abstract P prossimoPuntoDiScelta(List<P> ps, P p);

    protected abstract boolean esisteSoluzione(P p);

    protected abstract boolean ultimaSoluzione(P p);

    //factory
    protected abstract List<P> puntiDiScelta();

    protected abstract Collection<S> scelte(P p);

    protected final boolean tentativo(List<P> ps, P p) {
        Collection<S> sa = scelte(p);
        for (S s : sa) {
            System.out.println("Prima scelta: "+s+" per ps: "+p);
            if (ultimaSoluzione(p)){
                System.out.println("ultima soluzione");
                break;
            }
            if (assegnabile(p, s)) {
                //assegna(p,s);
                //System.out.println("assegnabile");
                if (esisteSoluzione(p)) {
                    System.out.println("esiste soluzione");
                    scriviSoluzione();
                    return true;
                } else {
                    if (tentativo(ps, prossimoPuntoDiScelta(ps, p)))
                        return true;
                }
                tentativo(ps, prossimoPuntoDiScelta(ps, p));
            }tentativo(ps, prossimoPuntoDiScelta(ps, p));


        }
        return false;
    } // tentativo

    protected abstract void risolvi();
}

