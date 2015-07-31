package dkd;

import gate.Annotation;
import gate.AnnotationSet;
import gate.GateConstants;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.ProcessingResource;
import gate.creole.metadata.AutoInstance;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.InvalidOffsetException;
import java.util.Set;
import java.util.HashSet;

@CreoleResource(name = "Duplicate Remover", comment = "Remove Duplicate Mentions")
public class DupRemover extends AbstractLanguageAnalyser implements ProcessingResource {
    private static final long serialVersionUID = -2874636782869415162L;

    /**
     * Run the resource.
     * @throws ExecutionException
     */
    public void execute() throws ExecutionException {
        AnnotationSet mentions = document.getAnnotations().get("Mention");
        Set<Annotation> deletees = new HashSet<Annotation>();
        Set<Annotation> visited = new HashSet<Annotation>();

        for (Annotation a : mentions)
        {
            for (Annotation v : visited)
                if (a.coextensive(v) && a.getFeatures().get("inst").equals(v.getFeatures().get("inst")))
                    deletees.add(a);

            visited.add(a);
        }

        for (Annotation a : deletees )
            document.getAnnotations().remove(a);
    }
}
